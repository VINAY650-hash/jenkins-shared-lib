def call(Map pipelineParams){
def projectName = pipelineParams.DockerHubRepoName

pipeline {
 agent any
  environment {
    registry = "vinay075/${projectName}"
    registryCredential = 'vinay075'
    dockerImage = ''
  }
  stages {
    stage('Building image') {
      steps{
        script {
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    stage('push image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Remove old docker image') {
      steps{
        sh "docker rmi $registry:$BUILD_NUMBER"
      }
    }
  }
}
}
