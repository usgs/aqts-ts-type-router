@Library(value='iow-ecs-pipeline@2.2.0', changelog=false) _

pipeline {
  agent {
    node {
      label 'team:iow'
    }
  }
  stages {
    stage('Set Build Description') {
      steps {
        script {
          currentBuild.description = "Deploy to ${env.DEPLOY_STAGE}"
        }
      }
    }
    stage('get and install the zip file for lambda consumption') {
      agent {
        dockerfile {
			label 'team:iow'
		}
      }
      steps {
                script {
                    if ("${params.DEPLOY_STAGE}" == 'DEV') {
                        def secretsString = sh(script: '/usr/local/bin/aws ssm get-parameter --name "/aws/reference/secretsmanager/IOW_AWS" --query "Parameter.Value" --with-decryption --output text --region "us-west-2"', returnStdout: true).trim()
                        def secretsJson = readJSON text: secretsString
                        def iowDevAccountNumber = secretsJson.accountNumber
                        def devAccountRoleName = secretsJson.roleName

                        def assumeRoleName = "arn:aws:iam::$iowDevAccountNumber:role/$devAccountRoleName"
                        def assumeRoleResp = sh(script: "/usr/local/bin/aws sts assume-role --role-arn $assumeRoleName --role-session-name expt-session --duration-seconds 3600", returnStdout: true).trim()
                        def roleJson = readJSON text: assumeRoleResp
                        env.AWS_ACCESS_KEY_ID = roleJson.Credentials.AccessKeyId
                        env.AWS_SECRET_ACCESS_KEY = roleJson.Credentials.SecretAccessKey
                        env.AWS_SESSION_TOKEN = roleJson.Credentials.SessionToken
                        env.BUCKET = 'iow-cloud-applications-dev'
                    } else {
                        env.BUCKET = 'iow-cloud-applications'
                    }
                }
        sh '''
          curl ${SHADED_JAR_ARTIFACT_URL} -Lo aqts-ts-type-router-aws.jar
          ls -al
          npm install
          ls -al
          ./node_modules/serverless/bin/serverless.js deploy --stage ${DEPLOY_STAGE} --bucket ${BUCKET} --taggingVersion ${SHADED_JAR_VERSION}
          '''
      }
    }
  }
  post {
    always {
      script {
        pipelineUtils.cleanWorkspace()
      }
    }
  }
}
