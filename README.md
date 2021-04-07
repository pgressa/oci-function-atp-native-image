## Micronaut 2.4.2 Documentation

- [User Guide](https://docs.micronaut.io/2.4.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Oracle Functions GraalVM GitHub Workflow

Workflow file: [`.github/workflows/oci-functions-graalvm.yml`](.github/workflows/oci-functions-graalvm.yml)

### Workflow description
For pushes to the `master` branch, the workflow will:
1. Setup the build environment with respect to the selected java or GraalVM version.
2. Login to the [Oracle Cloud Infrastructure Registry (OCIR)](https://docs.cloud.oracle.com/en-us/iaas/Content/Registry/Concepts/registryoverview.htm).
3. Install and configure [Oracle Cloud Infrastructure CLI](https://docs.cloud.oracle.com/en-us/iaas/Content/API/Concepts/cliconcepts.htm).
4. Build, tag and push Docker image with Micronaut application to the OCIR.
5. Create or update [Oracle Cloud Functions](https://docs.cloud.oracle.com/en-us/iaas/Content/Functions/Concepts/functionsoverview.htm) with name `oci-function-atp-native-image` using built Docker container image.

### Dependencies on other GitHub Actions
- [Setup GraalVM `DeLaGuardo/setup-graalvm`](https://github.com/DeLaGuardo/setup-graalvm)

### Setup
Add the following GitHub secrets:

| Name | Description |
| ---- | ----------- |
| OCI_AUTH_TOKEN | OCI account auth token. |
| OCI_OCIR_REPOSITORY | (Optional) Docker image repository in OCIR. For image `iad.ocir.io/[tenancy name]/foo/bar:0.1`, the `foo` is an _image repository_. |
| OCI_USER_OCID | OCI user ocid. |
| OCI_TENANCY_OCID | OCID of the tenancy. |
| OCI_KEY_FILE | OCI api signing private key file. See more on [Setup of API signing key](https://docs.cloud.oracle.com/en-us/iaas/Content/Functions/Tasks/functionssetupapikey.htm). |
| OCI_FINGERPRINT | OCI Api signing key file fingerprint. |
| OCI_PASSPHRASE | Passphrase to the private key file. Required only when passphrase is needed by the private key file. |
| OCI_FUNCTION_APPLICATION_OCID | Oracle function application OCID. See more on [Creating Applications](https://docs.cloud.oracle.com/en-us/iaas/Content/Functions/Tasks/functionscreatingapps.htm). |

The workflow file `.github/workflows/graalvm.yml` also contains additional configuration options that are now configured to:
| Name | Description | Default value |
| ---- | ----------- | ------------- |
| OCI_REGION | Oracle Infrastructure Cloud region. See more on [Regions and Availability Domains](https://docs.cloud.oracle.com/en-us/iaas/Content/General/Concepts/regions.htm).  | `us-ashburn-1` |
| OCI_OCIR_URL | Oracle Cloud Infrastructure Registry region endpoint. See more on [Regional availability](https://docs.cloud.oracle.com/en-us/iaas/Content/Registry/Concepts/registryprerequisites.htm#regional-availability). | `iad.ocir.io` |
| OCI_FUNCTION_MEMORY_IN_MBS | Maximum memory threshold for a function. See more on [Changing Oracle Functions Default Behavior](https://docs.cloud.oracle.com/en-us/iaas/Content/Functions/Tasks/functionscustomizing.htm) | `128` |
| OCI_FUNCTION_TIMEOUT_IN_SECONDS | Timeout for executions of the function. Value in seconds. | `120` |

### Verification
Follow instructions in [Micronaut Oracle Cloud documentation](https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/#httpFunctions) on how to configure the [Oracle API Gateway](https://docs.cloud.oracle.com/en-us/iaas/Content/APIGateway/Concepts/apigatewayoverview.htm) and invoke the Function.
For more invocation options visit [Invoking Functions](https://docs.cloud.oracle.com/en-us/iaas/Content/Functions/Tasks/functionsinvokingfunctions.htm).

## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)

## Feature github-workflow-oracle-oci-functions-graalvm documentation

- [https://docs.github.com/en/free-pro-team@latest/actions](https://docs.github.com/en/free-pro-team@latest/actions)

## Feature oracle-function documentation

- [Micronaut Oracle Function Support documentation](https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/#functions)

- [https://docs.cloud.oracle.com/iaas/Content/Functions/Concepts/functionsoverview.htm](https://docs.cloud.oracle.com/iaas/Content/Functions/Concepts/functionsoverview.htm)

## Feature oracle-cloud-sdk documentation

- [Micronaut Oracle Cloud SDK documentation](https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/)

- [https://docs.cloud.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdk.htm](https://docs.cloud.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdk.htm)

## Feature oracle-function-http documentation

- [Micronaut Oracle Function Support documentation](https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/#httpFunctions)



# Make it work in OCI Functions

Create dynamic group with compartment id where the function will be deployed:
```
Any {resource.type = 'fnfunc', resource.compartment.id = 'oocid1.compartment.oc1.....'}
```

Create policy to allow management of databases:
```
Allow dynamic-group <GROUP-NAME> to manage autonomous-database-family in compartment <COMPARTMENT-NAME>
```

Wait 15 minutes, since the resource principal refresh token is valid for 15 minutes.
