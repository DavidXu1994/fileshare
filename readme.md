��Ŀ�ܹ���Springboot+mybatis+thymeleaf+bootstrap
���ݿ⣺mysql
web��������tomcat��springboot��Ƕ��
==========================================

����Ŀ¼
Java
controller�����Ʋ㣬���ж�ǰ̨�ṩ�ӿ����
entity��
po��ʵ����󣬺����ݿ���Ӧ
vo����̨����ǰ̨��װ�����ֶ����
dto����̨֮�����ݴ���

mybatis��
dao�����ݿ⽻��
mapper��mybatis�ӿ�
service��ҵ�񷽷����ӿ�����
serviceImpl��ʵ��service�ӿ�
FileshareApplication��main�������

resource
mappers��mybatis�����ļ�
templates��htmlҳ��

application.properties:�����ļ�

pom.xml:jar������

==========================================
ִ������
controller-->service-->serviceImpl-->mapper-->mappers.xml-->�������controller--->thymeleaf����--->html+bootstrap չʾ��Ⱦ

controller��@RequestMapping ·��Ψһ��ModelAndView ��������ҳ������һ�£�
mappers.xml:namespace��ID��typeע������



