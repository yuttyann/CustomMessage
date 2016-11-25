#API解説不完全版(詳しくは[CustomMessage.java](https://goo.gl/gqwULk "")をどうぞ)
全バージョン対応:  
String CustomMessage.getAPI().getItemName(player, nullstr);    

getItemName説明：  
アイテム名を取得するために作りました。  
手に何も持っていない場合に任意の文字列を返すことができます。  
CrackShotの銃に対応しています。弾数を文字列から削除した武器名を返します。  
旧仕様の機能を利用したい場合は_Oldのついたメゾッドを使用してください。    
旧 CustomMessage.getAPI().getItemName_Old(プレイヤー, 何も持っていない場合に返す文字列); (非推奨)  
新 CustomMessage.getAPI().getItemName(プレイヤー, 何も持っていない場合に返す文字列);    

1.8～のみ対応 ※SpigotProtocolHack1.7-1.8でも使用可能:    
void CustomMessage.getAPI().sendFullTitle(player, fadein, stay, fadeout, title, subtitle);  
void CustomMessage.getAPI().sendFullTabTitle(player, header, footer);    

特に説明はいらないと思うので使い方は感じとってください：  
CustomMessage.getAPI().sendFullTitle(プレイヤー, 表示するまでの時間, 表示させている時間, 消えるまでの時間, タイトル[上], サブタイトル[下]);  
CustomMessage.getAPI().sendFullTabTitle(プレイヤー, タブタイトル[上], タブタイトル[下]); 