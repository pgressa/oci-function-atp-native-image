package com.example;

import com.oracle.bmc.ClientConfiguration;
import com.oracle.bmc.auth.ResourcePrincipalAuthenticationDetailsProvider;
import com.oracle.bmc.database.DatabaseClient;
import com.oracle.bmc.database.model.AutonomousDatabaseSummary;
import com.oracle.bmc.database.model.GenerateAutonomousDatabaseWalletDetails;
import com.oracle.bmc.database.requests.GenerateAutonomousDatabaseWalletRequest;
import com.oracle.bmc.database.requests.ListAutonomousDatabasesRequest;
import com.oracle.bmc.database.responses.ListAutonomousDatabasesResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.MediaType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.oraclecloud.core.TenancyIdProvider;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Controller("/ociFunctionAtpNativeImage")
public class OciFunctionAtpNativeImageController {

    @Inject
    DataSource dataSource;

    @Inject
    ResourcePrincipalAuthenticationDetailsProvider detailsProvider;

    @Inject
    ClientConfiguration clientConfiguration;

    @Inject
    DatabaseClient databaseClient;

    @Inject
    TenancyIdProvider tenancyIdProvider;

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/source")
    public String datasource() throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM DUAL");
        resultSet.next();
        return resultSet.getString(1) + " Files";
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public List<String> index(){
        ListAutonomousDatabasesResponse listAutonomousDatabasesResponse = databaseClient.listAutonomousDatabases(ListAutonomousDatabasesRequest.builder().compartmentId("ocid1.compartment.oc1..aaaaaaaanvwcpm4etg7mp4cm45r657dq4tlygvosxc7hagp3dhfjgn2zavzq").build());
        return listAutonomousDatabasesResponse.getItems().stream().map(AutonomousDatabaseSummary::getDbName).collect(Collectors.toList());
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/tenancy")
    public String tenancy(){
        return tenancyIdProvider.getTenancyId();
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get("/compartment")
    public String compartment(){
        return detailsProvider.getStringClaim(ResourcePrincipalAuthenticationDetailsProvider.ClaimKeys.COMPARTMENT_ID_CLAIM_KEY);
    }
}
