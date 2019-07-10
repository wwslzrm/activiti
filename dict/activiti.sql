/*
 Navicat Premium Data Transfer

 Source Server         : 39.104.108.81
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 39.104.108.81:3306
 Source Schema         : lockinlifeiot

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 05/07/2019 14:46:19
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
  `curDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前岗位',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '开始审批时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束审批时间',
  `processId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '流程编号',
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批结果(A: 同意， D: 不同意， T：不处理)',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人员',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '区域',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `apprvReason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批结果(A: 同意， D: 不同意， UL: 用锁中， T：不处理)',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '授权所属机构',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `apprvReason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for act_handle_info
-- ----------------------------
DROP TABLE IF EXISTS `act_handle_info`;
CREATE TABLE `act_handle_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `srcDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '来源岗位',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '处理时间',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作人员',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '异常原因',
  `apprvRs` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '处理结果',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '类型',
  `nodeorder` int(10) NULL DEFAULT NULL COMMENT '执行顺序',
  `oprDep` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '操作岗位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_node_info
-- ----------------------------
INSERT INTO `act_node_info` VALUES (1, '1', 'handleExp', '异常岗', 'handleExp', 'All', 'manual', -1, 'handleExp');

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
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_process_info
-- ----------------------------
INSERT INTO `act_process_info` VALUES (14, 'unLockTaskProcess', '2019-06-25 11:37:39', '4fdd3978-5fee-4d16-9f40-cb0f17504544', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (15, 'unLockTaskProcess', '2019-06-25 11:46:26', '72330b85-f898-4171-80ec-a0c80cea77bd', '1', '开锁任务工作流');
INSERT INTO `act_process_info` VALUES (16, 'unLockTaskProcess', '2019-06-25 12:45:03', '14f23b8d-061d-4707-8cd1-0dc6f796a1f0', '0', '开锁任务工作流');

-- ----------------------------
-- Table structure for act_role_assign
-- ----------------------------
DROP TABLE IF EXISTS `act_role_assign`;
CREATE TABLE `act_role_assign`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `applcNum` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '申请编号',
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批部门',
  `tel` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审批员手机号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of act_role_assign
-- ----------------------------
INSERT INTO `act_role_assign` VALUES (1, '1002', 'jingli', '19906613916');
INSERT INTO `act_role_assign` VALUES (2, '1002', 'jingli2', '18767122930');
INSERT INTO `act_role_assign` VALUES (3, '1002', 'handleExp', '19906613916');

SET FOREIGN_KEY_CHECKS = 1;
