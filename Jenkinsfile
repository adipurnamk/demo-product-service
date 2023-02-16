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
                apt install openjdk-11-jre-headless -y
                # java already installed
                java -version
                apt-get update
                # apt-get install maven docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
                apt-get install maven -y
                mvn -version
                mvn clean package
                gcloud builds submit --tag gcr.io/qwiklabs-gcp-01-7297809b5f6f/demo-product-service:v1.0.0
                docker build -t gcr.io/qwiklabs-gcp-01-123d056fbdd7/demo-product-service:v1.0.0
                docker push
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
