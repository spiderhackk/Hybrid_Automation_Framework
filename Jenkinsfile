pipeline {
    agent {
        label 'local-machine'
    }

    parameters {
        // This creates a dropdown menu in Jenkins
        choice(
            name: 'TEST_GROUP',
            choices: ['sanity', 'regression', 'all'],
            description: 'Select TestNG group to run'
        )
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from Jenkins SCM configuration...'
                checkout scm
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Creating the Docker environment...'
                sh 'docker build -t automation-agent .'
            }
        }

        stage('Quality Check : Sanity') {
            steps {
                echo 'Checking env by running the sanity test'
                sh 'docker run --rm -v ${WORKSPACE}:/app automation-agent mvn clean test -Dgroups="sanity"'
            }
        }

        stage('Run Tests') {
            steps {
                echo 'Executing Selenium Tests with Group: ${params.TEST_GROUP}...'
                script {
                    if (params.TEST_GROUP == 'all') {
                        // Runs everything if 'all' is selected
                        sh "docker run --rm -v ${WORKSPACE}:/app automation-agent mvn clean test"
                    } else {
                        // Passes the specific group from the dropdown
                        sh "docker run --rm -v ${WORKSPACE}:/app automation-agent mvn clean test -Dgroups=${params.TEST_GROUP}"
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'reports/**, screenshots/**, target/surefire-reports/**', allowEmptyArchive: true
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'reports',
                reportFiles: 'report.html',
                reportName: 'Extent Report'
            ])
        }

        success {
            echo 'Pipeline SUCCESS'
        }

        failure {
            echo 'Pipeline FAILED'
        }
    }
}
