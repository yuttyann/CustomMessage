# CustomMessage v5.1
このプラグインはyuttyann44581によって作成されています。  
Minecraftの様々なメッセージを変更できるプラグインです。  

# Commands
/rules  
/custommessage reload  

# Permissions
custommessage.rules  
custommessage.reload  

# API  
全バージョン対応:  
CustomMessageAPI.getItemName(player, nullmessage);  
プレイヤーが何を持っているのかをメッセージで表示したり、PlayerDeathEventなどで使えます。  
getItemName説明：  
CustomMessageAPI.getItemName(プレイヤー, 何も持っていない場合のメッセージ);  
9月28日追記： アイテム名を返しているだけなのでsendMessageやbroadcastMessageなどで表示させてください。  
1.8.xのみ対応 ※SpigotProtocolHackでも使用可能:  
CustomMessageAPI.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
CustomMessageAPI.sendTabTitle(player, header, footer);  
setTitle説明：  
CustomMessageAPI.sendTitle(プレイヤー, 表示するまでの時間, 表示させている時間, 消えるまでの時間, タイトル, サブタイトル);  
setTabTitle説明：  
CustomMessageAPI.sendTabTitle(プレイヤー, Tabを押したときに上に表示されるメッセージ, Tabを押したときに下に表示されるメッセージ);  