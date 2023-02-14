pipeline {
  agent {
    kubernetes {
      // Without cloud, Jenkins will pick the first cloud in the list
      cloud "jenkins-agent"
      label "jenkins-agent"
      yamlFile "jenkins-build-pod.yaml"
    }
  }

  stages {
    stage("Build") {
      steps {
        // dir("hello-app") {
          container("gcloud") {
            // Cheat by using Cloud Build to help us build our container
            sh "gcloud config get-value project"
            sh "sudo apt install openjdk-11-jdk-headless maven -y"
            sh "mvn clean package" 
            sh "gcloud builds submit --region=us-west2 --tag us-west2-docker.pkg.dev/$PROJECT_ID/demo-product-service:v1.0.0"
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