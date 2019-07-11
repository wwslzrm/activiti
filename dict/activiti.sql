/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : lockinlifeiot

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 11/07/2019 14:31:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for act_approve_his
-- ----------------------------
DROP TABLE IF EXISTS `act_approve_his`;
CREATE TABLE `act_approve_his`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `srcDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源岗位',
  `srcId` int(11) NULL DEFAULT NULL COMMENT '来源岗位ID',
  `curDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前岗位',
  `curId` int(11) NULL DEFAULT NULL COMMENT '当前岗位ID',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '开始审批时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束审批时间',
  `processId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程编号',
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批结果',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人员',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区域',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `apprvReason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for act_approve_rs
-- ----------------------------
DROP TABLE IF EXISTS `act_approve_rs`;
CREATE TABLE `act_approve_rs`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `srcDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源岗位',
  `curDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前岗位',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `processId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程编号',
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批结果',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权所属机构',
  `applcNum` int(11) NULL DEFAULT NULL COMMENT '申请编号',
  `apprvReason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for act_handle_info
-- ----------------------------
DROP TABLE IF EXISTS `act_handle_info`;
CREATE TABLE `act_handle_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `srcId` int(11) NULL DEFAULT NULL COMMENT '来源岗位ID',
  `srcDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源岗位',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人员',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '异常原因',
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 83 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for act_node_deploy
-- ----------------------------
DROP TABLE IF EXISTS `act_node_deploy`;
CREATE TABLE `act_node_deploy`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `processName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流程名称',
  `processTag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流程中文名称',
  `ename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '节点名称',
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文名称',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '执行方法',
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '执行类',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型（manual：人工岗； auto：自动岗）',
  `nodeOrder` int(11) NULL DEFAULT NULL COMMENT '执行顺序（数字越小执行顺序越高）',
  `oprDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作岗位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_node_deploy
-- ----------------------------
INSERT INTO `act_node_deploy` VALUES (1, 'unLockTaskProcess', '开锁任务工作流', 'beginNode', '开始节点', 'beginNode', 'unLockTaskProcess', 'auto', 0, 'auto');
INSERT INTO `act_node_deploy` VALUES (2, 'unLockTaskProcess', '开锁任务工作流', 'errorNode', 'errorNode', 'errorNode', 'unLockTaskProcess', 'auto', 1, 'jingli');
INSERT INTO `act_node_deploy` VALUES (3, 'unLockTaskProcess', '开锁任务工作流', 'testNode', '测试节点', 'testNode', 'unLockTaskProcess', 'manual', 2, 'jingli');
INSERT INTO `act_node_deploy` VALUES (4, 'unLockTaskProcess', '开锁任务工作流', 'test2Node', '测试节点', 'test2Node', 'unLockTaskProcess', 'manual', 3, 'jingli2');
INSERT INTO `act_node_deploy` VALUES (5, 'unLockTaskProcess', '开锁任务工作流', 'endNode', '结束节点', 'endNode', 'unLockTaskProcess', 'auto', 999, 'auto');

-- ----------------------------
-- Table structure for act_node_info
-- ----------------------------
DROP TABLE IF EXISTS `act_node_info`;
CREATE TABLE `act_node_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `processId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '流程ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '节点名称',
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '中文名称',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '执行方法',
  `clazz` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '执行类',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型（manual：人工岗； auto：自动岗）',
  `nodeOrder` int(11) NULL DEFAULT NULL COMMENT '执行顺序（数字越小执行顺序越高）',
  `oprDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作岗位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_node_info
-- ----------------------------
INSERT INTO `act_node_info` VALUES (88, '17308914-ef63-4746-a47f-808b7303afc7', 'endNode', '结束节点', 'endNode', 'unLockTaskProcess', 'auto', 999, 'auto');
INSERT INTO `act_node_info` VALUES (89, '17308914-ef63-4746-a47f-808b7303afc7', 'beginNode', '开始节点', 'beginNode', 'unLockTaskProcess', 'auto', 0, 'auto');
INSERT INTO `act_node_info` VALUES (90, '17308914-ef63-4746-a47f-808b7303afc7', 'errorNode', 'errorNode', 'errorNode', 'unLockTaskProcess', 'auto', 1, 'jingli');
INSERT INTO `act_node_info` VALUES (91, '17308914-ef63-4746-a47f-808b7303afc7', 'errorNode', 'errorNode', 'errorNode', 'unLockTaskProcess', 'auto', 2, 'jingli');
INSERT INTO `act_node_info` VALUES (92, '17308914-ef63-4746-a47f-808b7303afc7', 'errorNode', 'errorNode', 'errorNode', 'unLockTaskProcess', 'auto', 3, 'jingli');
INSERT INTO `act_node_info` VALUES (93, '000', 'handleExp', '异常处理', 'ALL', 'ALL', 'manual', -10, 'handleExp');
INSERT INTO `act_node_info` VALUES (94, '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', 'beginNode', '开始节点', 'beginNode', 'unLockTaskProcess', 'auto', 0, 'auto');
INSERT INTO `act_node_info` VALUES (95, '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', 'errorNode', 'errorNode', 'errorNode', 'unLockTaskProcess', 'auto', 1, 'jingli');
INSERT INTO `act_node_info` VALUES (96, '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', 'testNode', '测试节点', 'testNode', 'unLockTaskProcess', 'manual', 2, 'jingli');
INSERT INTO `act_node_info` VALUES (97, '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', 'test2Node', '测试节点', 'test2Node', 'unLockTaskProcess', 'manual', 3, 'jingli2');
INSERT INTO `act_node_info` VALUES (98, '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', 'endNode', '结束节点', 'endNode', 'unLockTaskProcess', 'auto', 999, 'auto');

-- ----------------------------
-- Table structure for act_process_info
-- ----------------------------
DROP TABLE IF EXISTS `act_process_info`;
CREATE TABLE `act_process_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批类型（activiti注解定义）',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT 'UUID',
  `state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态（0：当前流程；1：历史流程）',
  `cname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批类型中文名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_process_info
-- ----------------------------
INSERT INTO `act_process_info` VALUES (11, 'unLockTaskProcess', '2019-06-25 12:11:10', '359d864d-e059-467b-971d-752fac4c955e', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (12, 'unLockTaskProcess', '2019-06-25 15:40:26', 'd00ec9df-4c5e-4453-b51a-67f5e0bd2fb2', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (13, 'unLockTaskProcess', '2019-06-25 16:13:32', '040ad650-4dd9-410e-ad89-28010d81c172', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (14, 'unLockTaskProcess', '2019-07-08 17:08:34', '55d3be31-9df2-4016-b1e0-5d87850f2eaf', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (15, 'unLockTaskProcess', '2019-07-08 17:26:19', '566bfa90-e432-47e9-8f02-cb6942d83162', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (16, 'unLockTaskProcess', '2019-07-08 17:33:02', 'c0ceee0d-98f6-4203-b7bf-7bebafa3a318', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (17, 'unLockTaskProcess', '2019-07-08 17:33:16', 'fbde2e1a-48cc-4210-95ef-069db55875be', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (18, 'unLockTaskProcess', '2019-07-09 08:46:50', '5e80c1f7-1ef8-4081-820e-0b7a0ec81550', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (19, 'unLockTaskProcess', '2019-07-09 10:33:07', 'e2f7478c-22d7-4309-a638-d89726441737', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (20, 'unLockTaskProcess', '2019-07-09 10:34:00', '17308914-ef63-4746-a47f-808b7303afc7', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (21, 'unLockTaskProcess', '2019-07-11 14:21:57', '2d082bd1-e1a2-4fba-9e37-a9b71f7bce89', '0', '开锁任务工作流');

-- ----------------------------
-- Table structure for act_role_assign
-- ----------------------------
DROP TABLE IF EXISTS `act_role_assign`;
CREATE TABLE `act_role_assign`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批部门',
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批员手机号',
  `nodeOrder` int(11) NULL DEFAULT NULL COMMENT '流程节点序号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_role_assign
-- ----------------------------
INSERT INTO `act_role_assign` VALUES (1, '1002', 'jingli', '19906613916', NULL);
INSERT INTO `act_role_assign` VALUES (2, '1002', 'jingli2', '18767122930', NULL);
INSERT INTO `act_role_assign` VALUES (3, '1002', 'handleExp', '19906613916', NULL);
INSERT INTO `act_role_assign` VALUES (4, '5a55346c3d5242d4b9eb47c8e3af89e9', 'admin', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (5, '5a55346c3d5242d4b9eb47c8e3af89e9', 'own', '19906613916', NULL);
INSERT INTO `act_role_assign` VALUES (6, '215c29e3491442748e043e609f67b057', 'admin', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (7, '215c29e3491442748e043e609f67b057', 'admin', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (8, '42f8863f6408455ea079fb18f33586cf', 'admin', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (9, '42f8863f6408455ea079fb18f33586cf', 'own', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (10, '19f9407404e645e1a99e9e19d976a11f', 'admin', '18786886535', NULL);
INSERT INTO `act_role_assign` VALUES (11, '19f9407404e645e1a99e9e19d976a11f', 'own', '13456392382', NULL);

SET FOREIGN_KEY_CHECKS = 1;
