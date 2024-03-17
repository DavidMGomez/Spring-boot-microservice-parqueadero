@Library('ceiba-jenkins-library') _
pipeline {
  //Donde se va a ejecutar el Pipeline
  agent {
    label 'Slave_Induccion'
  }

  //Opciones específicas de Pipeline dentro del Pipeline
  options {
    	buildDiscarder(logRotator(numToKeepStr: '3'))
 		disableConcurrentBuilds()
  }

  //Una sección que define las herramientas “preinstaladas” en Jenkins
  tools {
    jdk 'JDK11_Centos' //Versión preinstalada en la Configuración del Master
    nodejs 'NodeJS15'
  }

  //Aquí comienzan los “ítems” del Pipeline
  stages{

    stage('Checkout') {
      steps{
        echo "------------>Checkout<------------"
         checkout scm
      }
    }

    stage('Compile & Unit Tests') {
      steps{
        echo "------------>Compile & Unit Tests<------------"
        dir('microservicio') {
          sh 'chmod +x gradlew'
          sh './gradlew --b ./build.gradle clean'
          sh './gradlew --b ./build.gradle test'
          junit 'dominio/build/test-results/test/*.xml'
        // junit 'infraestructura/build/test-results/test/*.xml'
        // junit 'aplicacion/build/test-results/test/*.xml'
        //RUTA RELATIVA DE LOS ARCHIVOS .XML resultado de las pruebas con JUnit
        }
      }
    }

    stage('Static Code Analysis') {
      steps{

           sonarqubeMasQualityGatesP(sonarKey:'co.com.ceiba.adn:parqueadero.david.gomez',
                   sonarName:'ADN-Parqueadero-david.gomez',
                   sonarPathProperties:'./sonar-project.properties')
           }
      }

    stage('Build') {
      steps {
        echo "------------>Build<------------"

         dir('microservicio') {
              sh './gradlew --b ./build.gradle build -x test'
            }
      }
    }
  }

  post {
    always {
      echo 'This will always run'
    }
    success {
      echo 'This will run only if successful'
    }
    failure {
      echo 'This will run only if failed'
      echo 'This will run only if failed'
      mail (to: 'david.gomez@ceiba.com.co',subject: "Failed Pipeline:${currentBuild.fullDisplayName}",body: "Something is wrong with ${env.BUILD_URL}")

    }
    unstable {
      echo 'This will run only if the run was marked as unstable'
    }
    changed {
      echo 'This will run only if the state of the Pipeline has changed'
      echo 'For example, if the Pipeline was previously failing but is now successful'
    }
  }
}
