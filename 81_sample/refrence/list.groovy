#!groovy

//項目がリストに含まれているかどうか
assert ['神川','伊藤','山田','篠原','田口'].contains('伊藤')

//list内すべての項目があるかどうか調べる
assert [2, 5, 8, 12, 16].containsAll([2,8,12])
assert [2, 5, 8, 12, 16].containsAll([2,8,13])==false

//リストの項目がかぶっていないか調べる
assert ['神川','伊藤'].disjoint(['山田','篠原','田口'])

//リストの指定位置にまとめて項目を追加
def names = ['近藤','加藤','山本','池田']
names.addAll( 2, ['川村','吉村'])
assert names == ['近藤','加藤','川村','吉村','山本','池田']

//リスト同士で共通な項目を出す
def nc = ['山本','加藤','神川','伊藤']
def ap = ['近藤','加藤','山本','池田']
def kyoutu = nc.intersect(ap)
assert kyoutu == ['加藤','山本']

//3より大きいものを探す ※最初にヒットしたものが返される
assert [2, 8, 4, 3, 6].find { it > 3 } == 8

//「村」のつく名前を探す
def list = ['渡辺','川村','吉村','長谷川']
def mura = list.findAll{it=~/村/}
assert mura == ['川村','吉村']
assert mura.size() == 2

//15より大きいものがあるか調べる
assert [2, 5, 8, 12, 16].any { it > 15 } == true
assert [13, 15, 2, 9, 3].any { it > 15 } == false

//すべての値が2より大きいか調べる
assert [2, 5, 8, 12, 16].every{ it > 2 } == false
assert [13, 15, 6, 9, 3].every{ it > 2 } == true

//リストの値を加工した新たなリストを作る
assert [1, 2, 3].collect{ "hoge" * it } == ["hoge", "hogehoge", "hogehogehoge"]

//Listにない項目は全て削除
def myList=[5, 8, 12, 16]
myList.retainAll([2,8,12])
assert myList==[8, 12]

//Listにある項目は全て削除
def myList2=[5, 8, 12, 16]
myList2.removeAll([2,8,12])
assert myList2==[5, 16]

//全ての要素を削除
myList.clear()
assert myList==[]
assert myList.isEmpty()

//listの一部を抜き出し。fromIndexは含む、toIndexは含まない
assert [2, 3, 5, 8, 11, 12, 16].subList(4,6)==[11,12]

//List内の要素の全ての組み合わせを出力(HashSetが返る)
def pt = [2,3,5].permutations()
assert [2,3,5].permutations()==[[3,2,5],[3,5,2],[5,2,3],[2,5,3],[2,3,5],[5,3,2]] as HashSet
assert [2,3,5].subsequences()==[[3],[2],[5],[2,3,5],[3,5],[2,3],[2,5]] as HashSet

//反転
assert [2,3,5,8,11,12,16].reverse()==[16,12,11,8,5,3,2]
