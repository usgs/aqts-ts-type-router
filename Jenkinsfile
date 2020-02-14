@Library(value='iow-ecs-pipeline@1.0.0', changelog=false) _

pipeline {
  agent {
    node {
      label 'project:any'
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
        dockerfile true
      }
      steps {
        sh '''
          curl ${SHADED_JAR_ARTIFACT_URL} -Lo aqts-ts-type-router-aws.ja
          ls -al
          npm install serverless
          ls -al
          ./node_modules/serverless/bin/serverless deploy --stage ${DEPLOY_STAGE}
          '''
      }
    }
  }
}