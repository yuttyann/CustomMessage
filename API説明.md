#API����s���S��(�ڂ�����CustomMessage.java���ǂ���)  �S�o�[�W�����Ή�:  
ItemStack CustomMessage.getAPI().getItemInHand(player);  
String CustomMessage.getAPI().getItemName(player, nullmessage);    
getItemInHand�����F  
1.9����getItemInHand���񐄏��ɂȂ�擾���@���ς�������ߍ��܂����B  
1.7�`1.10�܂Ő���ɓ��삵�Ă���̂��m�F���Ă��܂��B
CustomMessage.getAPI().getItemInHand(�v���C���[);    
getItemName�����F  
�A�C�e�������擾���邽�߂ɍ��܂����B  
��ɉ��������Ă��Ȃ��ꍇ�Ɏw�肵��������Ԃ����Ƃ��ł��܂��B  
CrackShot�̏e�ɑΉ����Ă��܂��B�e���𕶎��񂩂�폜�������햼��Ԃ��܂��B  
���A�C�e�����擾�𗘗p�������ꍇ��_Old�̂������]�b�h���g�p���Ă��������B    
�� CustomMessage.getAPI().getItemName_Old(�v���C���[, ���������Ă��Ȃ��ꍇ�̃��b�Z�[�W); (�񐄏�)  
�V CustomMessage.getAPI().getItemName(�v���C���[, ���������Ă��Ȃ��ꍇ�̃��b�Z�[�W);    

1.8�`�̂ݑΉ� ��SpigotProtocolHack1.7-1.8�ł��g�p�\:    
void CustomMessage.getAPI().sendFullTitle(player, fadeIn, stay, fadeOut, title, subtitle);  
void CustomMessage.getAPI().sendFullTabTitle(player, header, footer);    
���ɐ����͂���Ȃ��Ǝv���̂Ŏg�����͊����Ƃ��Ă��������F  
CustomMessage.getAPI().sendFullTitle(�v���C���[, �\������܂ł̎���, �\�������Ă��鎞��, ������܂ł̎���, �^�C�g��, �T�u�^�C�g��);  
CustomMessage.getAPI().sendFullTabTitle(�v���C���[, Tab���������Ƃ��ɏ�ɕ\������郁�b�Z�[�W, Tab���������Ƃ��ɉ��ɕ\������郁�b�Z�[�W); 