# CustomMessage v5.1
���̃v���O�C����yuttyann44581�ɂ���č쐬����Ă��܂��B  
Minecraft�̗l�X�ȃ��b�Z�[�W��ύX�ł���v���O�C���ł��B  

# Commands
/rules  
/custommessage reload  

# Permissions
custommessage.rules  
custommessage.reload  

# API  
�S�o�[�W�����Ή�:  
CustomMessageAPI.getItemName(player, nullmessage);  
�v���C���[�����������Ă���̂������b�Z�[�W�ŕ\��������APlayerDeathEvent�ȂǂŎg���܂��B  
getItemName�����F  
CustomMessageAPI.getItemName(�v���C���[, ���������Ă��Ȃ��ꍇ�̃��b�Z�[�W);  
9��28���ǋL�F �A�C�e������Ԃ��Ă��邾���Ȃ̂�sendMessage��broadcastMessage�Ȃǂŕ\�������Ă��������B  
1.8.x�̂ݑΉ� ��SpigotProtocolHack�ł��g�p�\:  
CustomMessageAPI.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
CustomMessageAPI.sendTabTitle(player, header, footer);  
setTitle�����F  
CustomMessageAPI.sendTitle(�v���C���[, �\������܂ł̎���, �\�������Ă��鎞��, ������܂ł̎���, �^�C�g��, �T�u�^�C�g��);  
setTabTitle�����F  
CustomMessageAPI.sendTabTitle(�v���C���[, Tab���������Ƃ��ɏ�ɕ\������郁�b�Z�[�W, Tab���������Ƃ��ɉ��ɕ\������郁�b�Z�[�W);  