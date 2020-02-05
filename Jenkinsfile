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
    stage('build the zip file for lambda consumption') {
      agent {
        dockerfile true
      }
      steps {
        sh '''
          ls -al
          npm install serverless
          ls -al
          ./node_modules/serverless/bin/serverless deploy --stage ${DEPLOY_STAGE}
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