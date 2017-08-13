#!/usr/bin/env groovy

stage('build')
{
    node('master')
    {
        echo 'Building..'
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