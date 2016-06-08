# CustomMessage Readme
このプラグインはyuttyann44581によって作成されています。  
Minecraftの様々なメッセージを変更できるプラグインです。  
ライセンス: LGPLv3    

# Commands
/rules  
/custommessage reload  
/title <player> <title> <subtitle>  
/title tab <player> <header> <footer>  

# Permissions
custommessage.command.rules  
custommessage.command.reload  
custommessage.command.title  

# API  
全バージョン対応:  
CustomMessage.getAPI().getItemInHand(player);  
CustomMessage.getAPI().getItemName(player, nullmessage);  
getItemInHand説明：  
CustomMessage.getAPI().getItemInHand(プレイヤー);  
getItemName説明：  
CustomMessage.getAPI().getItemName(プレイヤー, 何も持っていない場合のメッセージ);  

1.8〜のみ対応 ※SpigotProtocolHackでも使用可能:  
CustomMessage.getAPI().sendFullTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
CustomMessage.getAPI().sendFullTabTitle(player, header, footer);  
setTitle説明：  
CustomMessage.getAPI().sendFullTitle(プレイヤー, 表示するまでの時間, 表示させている時間, 消えるまでの時間, タイトル, サブタイトル);  
setTabTitle説明：  
CustomMessage.getAPI().sendFullTabTitle(プレイヤー, Tabを押したときに上に表示されるメッセージ, Tabを押したときに下に表示されるメッセージ);  