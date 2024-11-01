pipeline {
    agent any
    tools { 
      maven 'myMaven' 
    }
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }
    }
}