
def buildImage(){
    echo "building the docker image"
    withCredentials([usernamePassword(credentialsId:"dockerhub-cred",usernameVariable:"USER",passwordVariable:"PASS")]){
        sh "docker build -t srvwin/dockerinitial:react-nodeapp-1.0 ."

        sh "echo $PASS | docker login -u $USER --password-stdin"


        sh "docker push  srvwin/dockerinitial:react-nodeapp-1.0"
    }}
def deployApp(){
    def dockerRunCommand = "docker run -d -p 3000:3080 srvwin/dockerinitial:react-nodeapp-1.0"
    echo "deploying the code"

    sshagent(['ec2-node-react']) {
        sh "ssh -o StrictHostKeyChecking=no ec2-user@13.201.191.234 ${dockerRunCommand}"
    }



}
return this