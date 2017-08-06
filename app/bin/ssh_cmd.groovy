#!/usr/bin/groovy

// 変数定義
def server = args[0]
def cmd = args[1]

// 設定ファイルロード
config = ['path':'/opt/app/conf', 'file':'env.groovy']
env = new ConfigSlurper().parse(new File(config['path'] + "/" + config['file']).toURL())

// コマンド組立
def ssh_cmd = [
    ('ssh -i ' + env.session.ssh."${server}".identity),
    ('-p '     + env.session.ssh."${server}".port),
    (            env.session.ssh."${server}".user + '@' + env.session.ssh."${server}".host),
    (cmd)
].join(' ')

// コマンド実行
def proc = ssh_cmd.execute().text
