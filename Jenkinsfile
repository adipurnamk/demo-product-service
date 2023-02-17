pipeline {
  agent {
    kubernetes {
      // Without cloud, Jenkins will pick the first cloud in the list
      cloud "jenkins-agent"
      // label "jenkins-agent"
      yamlFile "jenkins-build-pod.yaml"
    }
  }

  stages {
    stage("Build") {
      steps {
        // dir("hello-app") {
          container("gcloud") {
              withCredentials([file(credentialsId: 'qwiklabs', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
                // Cheat by using Cloud Build to help us build our container
                sh '''
                gcloud auth activate-service-account --key-file $GOOGLE_APPLICATION_CREDENTIALS
                gcloud config list account --format "value(core.account)"
                gcloud config get-value project
                
                # build jar phase
                
                apt-get update
                apt-get install maven -y
                mvn -version
                mvn clean package
                gcloud builds submit --tag gcr.io/qwiklabs-gcp-01-92f862dec37b/demo-product-service:v1.0.0
                '''
              }
          // }
        }
      }
    }

    stage("Deploy") {
      steps {
        container("kubectl") {
          sh "kubectl apply -f deployment.yaml" 
          sh "kubectl apply -f service.yaml" 
          // sh "kubectl rollout status deployments/hello-app"
        }
      }
    }
  }
}
