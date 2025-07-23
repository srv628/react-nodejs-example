def gv

pipeline {
    agent any
//     tools {
//         nodejs 'nodejs_22.x' // Ensure the name matches exactly as configured in Jenkins
//     }
    stages {
        stage('init') {
            steps {
                script {
                    gv = load 'script.groovy'
                }
            }
        }
        stage('install dependencies') {
            steps {
                script {
                    sh "/usr/bin/npm install"
                }
            }
        }
           stage('building the project') {
                    steps {
                        script {
                            sh "/usr/bin/npm run build"
                        }
                    }
                }

        stage('building the docker image') {
            steps {
                script {
                    gv.buildImage() // Ensure the method name is correct (buildImage instead of buildimage)
                }
            }
        }
        stage('deploying the image to the docker hub') {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}