
def buildImage(){
    echo "building the docker image"
    withCredentials([usernamePassword(credentialsId:"dockerhub-cred",usernameVariable:"USER",passwordVariable:"PASS")]){
        sh "docker build -t srvwin/dockerinitial:react-nodeapp-1.0 ."

        sh "echo $PASS | docker login -u $USER --password-stdin"


        sh "docker push  srvwin/dockerinitial:react-nodeapp-1.0"
    }}
def deployApp(){
    echo "deploying the code"

}
return this