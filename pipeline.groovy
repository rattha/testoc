node(){
    stage('checkout'){
        git credentialsId: '5c03ce9a-2df9-46f7-8256-b79e3d9b2562', url: 'https://github.com/rattha/testoc.git'
    }

    stage('test'){
        openshift.verbose()
        openshift.withCluster('mini') {
            openshift.withProject( 'myproject' ) {
                openshift.doAs('XXX'){

                    openshift.raw("annotate", "dc", "testapp", "kubectl.kubernetes.io/last-applied-configuration=''", "--overwrite")

                    def dc = readFile 'mydc.yml'
                    println('apply new=====')
                    openshift.apply(dc)
                }
            }
        }
    }
}