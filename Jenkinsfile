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
              withCredentials([file(credentialsId: 'service-account', variable: 'GOOGLE_APPLICATION_CREDENTIALS')]) {
                // Cheat by using Cloud Build to help us build our container
                sh '''
                # gcloud auth activate-service-account --key-file $GOOGLE_APPLICATION_CREDENTIALS
                gcloud config list account --format "value(core.account)"
                gcloud config get-value project
                apt install openjdk-11-jre-headless -y
                # java already installed
                java -version
                apt-get update
                apt-get install maven -y
                mvn -version
                mvn clean package
                gcloud builds submit --region=us-central1-c --tag gcr.io/qwiklabs-gcp-01-123d056fbdd7/demo-product-service:v1.0.0
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