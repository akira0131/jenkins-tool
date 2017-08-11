stage('build')
{
    node('master')
    {
        echo 'Building..'
    }
}

stage('user_input')
{
    node('master')
    {
        try {
                    // ユーザから入力を受け付ける
                    timeout(time: 10, unit: 'SECONDS')
                    {
                        try
                        {
                            // ユーザ設定ロード
                            userInput = input(
                                message             : '''\
                                                      |実行モードを選択してください。
                                                      |（デフォルト：ジョブ実行モード）'''.stripMargin(),
                                ok                  : '実行する',
                                parameters          : [
                                    choice(
                                        name        : '動作モード:',
                                        choices     : 'ジョブ実行モード\nジョブ設定モード',
                                        defaultValue: 'ジョブ実行モード'
                                    )
                                ]
                            )
                        } catch(Exception e) {

                            println userInput
                            throw e
                        }
                    }
                } catch(Exception e) {

                    // デフォルト値を設定
                    userInput = 'ジョブ実行モード'
                }

        println userInput

        // 実行モード判定
        if(userInput == 'ジョブ設定モード')
        {
            // ジョブ初期設定ロード
            try {
                def config = ['path':'/opt/app/conf', 'file':'job.groovy']
                def job = new ConfigSlurper().parse(new File(config['path'] + "/" + config['file']).toURL())
            } catch(Exception e) {}

            // ジョブ設定反映
            try
            {
                //
                triggers
                {
                    //
                    cron('* * * * *')
                }

                //
                options
                {
                    // ログ保持日数
                    buildDiscarder(logRoator(daysToKeepStr: '30'))
                }
                println 'ジョブの初期設定ロード完了'
                println '動作モードがジョブ設定モードのため、後続処理はキャンセルされます。'
            }
            catch(Exception e) {

                println 'ジョブの初期設定ロードに失敗しました。'
            }
        }
        else
        {
            println '動作モードがジョブ実行モードのため、ジョブ設定処理をスキップします。'
        }
    }
}

stage('mail_test')
{
    node('master')
    {
        // 外部ファイルからジョブ設定ロード
        try
        {
            def config = ['path':'/opt/app/conf', 'file':'env.groovy']
            def env = new ConfigSlurper().parse(new File(config['path'] + "/" + config['file']).toURL())
        } catch(Exception e){}

        emailext(
            to        : '${ENV, var="debug_mail_address"}',
            subject   : '${DEFAULT_SUBJECT}ABORT',
            body      : '''<p>ジョブ走行中に予期せぬエラーが発生しました。</p>
                          <p>解析をお願いします。</p>''',
            attachLog : false,
            mimeType  : 'text/html'
        )
    }
}