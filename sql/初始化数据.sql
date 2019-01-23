--初始化管理员账号
INSERT INTO t_user(`create_by` ,
  `create_time`,
  `update_by`,
  `update_time`,
  `is_deleted`,
  `user_name`,
  `password`) VALUES(0,NOW(),0,NOW(),0,'admin','72e435684f85272057912131e77c1062d25a80073bb0f29d');

--  初始化角色
INSERT INTO t_role(`role_name`,`descript`) VALUES('admin','超级管理员'),
('finance','财务'),('engineering','工程'),('warehouse','仓库'),('quality','品质');

--将超级管理员角色绑定到第一个初始化账户
INSERT INTO t_user_role(`role_id`,`user_id`) VALUES(1,1);
