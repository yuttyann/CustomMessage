# CustomMessage Readme
���̃v���O�C����yuttyann44581�ɂ���č쐬����Ă��܂��B  
Minecraft�̗l�X�ȃ��b�Z�[�W��ύX�ł���v���O�C���ł��B  
���C�Z���X: LGPLv3    

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
�S�o�[�W�����Ή�:  
CustomMessage.getAPI().getItemInHand(player);  
CustomMessage.getAPI().getItemName(player, nullmessage);  
getItemInHand�����F  
CustomMessage.getAPI().getItemInHand(�v���C���[);  
getItemName�����F  
CustomMessage.getAPI().getItemName(�v���C���[, ���������Ă��Ȃ��ꍇ�̃��b�Z�[�W);  

1.8-1.9�̂ݑΉ� ��SpigotProtocolHack�ł��g�p�\:  
CustomMessage.getAPI().sendFullTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
CustomMessage.getAPI().sendFullTabTitle(player, header, footer);  
setTitle�����F  
CustomMessage.getAPI().sendFullTitle(�v���C���[, �\������܂ł̎���, �\�������Ă��鎞��, ������܂ł̎���, �^�C�g��, �T�u�^�C�g��);  
setTabTitle�����F  
CustomMessage.getAPI().sendFullTabTitle(�v���C���[, Tab���������Ƃ��ɏ�ɕ\������郁�b�Z�[�W, Tab���������Ƃ��ɉ��ɕ\������郁�b�Z�[�W);  