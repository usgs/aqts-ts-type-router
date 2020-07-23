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
        sh '''
          curl ${SHADED_JAR_ARTIFACT_URL} -Lo aqts-ts-type-router-aws.jar
          ls -al
          npm install
          ls -al
          ./node_modules/serverless/bin/serverless.js deploy --stage ${DEPLOY_STAGE} --taggingVersion ${SHADED_JAR_VERSION}
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
