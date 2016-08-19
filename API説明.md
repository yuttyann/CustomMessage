#API解説不完全版(詳しくはCustomMessage.javaをどうぞ)  全バージョン対応:  
ItemStack CustomMessage.getAPI().getItemInHand(player);  
String CustomMessage.getAPI().getItemName(player, nullmessage);    
getItemInHand説明：  
1.9からgetItemInHandが非推奨になり取得方法が変わったため作りました。  
1.7～1.10まで正常に動作しているのを確認しています。
CustomMessage.getAPI().getItemInHand(プレイヤー);    
getItemName説明：  
アイテム名を取得するために作りました。  
手に何も持っていない場合に指定した文字を返すことができます。  
CrackShotの銃に対応しています。弾数を文字列から削除した武器名を返します。  
旧アイテム名取得を利用したい場合は_Oldのついたメゾッドを使用してください。    
旧 CustomMessage.getAPI().getItemName_Old(プレイヤー, 何も持っていない場合のメッセージ); (非推奨)  
新 CustomMessage.getAPI().getItemName(プレイヤー, 何も持っていない場合のメッセージ);    

1.8～のみ対応 ※SpigotProtocolHack1.7-1.8でも使用可能:    
void CustomMessage.getAPI().sendFullTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
void CustomMessage.getAPI().sendFullTabTitle(player, header, footer);    
特に説明はいらないと思うので使い方は感じとってください：  
CustomMessage.getAPI().sendFullTitle(プレイヤー, 表示するまでの時間, 表示させている時間, 消えるまでの時間, タイトル, サブタイトル);  
CustomMessage.getAPI().sendFullTabTitle(プレイヤー, Tabを押したときに上に表示されるメッセージ, Tabを押したときに下に表示されるメッセージ); 