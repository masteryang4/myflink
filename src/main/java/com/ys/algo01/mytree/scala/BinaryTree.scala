package com.ys.algo01.mytree.scala

/**
  * 定义二叉树，前序、中序、后序遍历，前序、中序、后序查找，删除节点
  */
class BinaryTree {
  var root: TreeNode = null

  //后序遍历
  def postOrder(): Unit = {
    if (root != null) {
      root.postOrder()
    } else {
      println("当前二叉树为空，不能遍历")
    }
  }

  //中序遍历
  def infixOrder(): Unit = {
    if (root != null) {
      root.infixOrder()
    } else {
      println("当前二叉树为空，不能遍历")
    }
  }

  //前序遍历
  def preOrder(): Unit = {
    if (root != null) {
      root.preOrder()
    } else {
      println("当前二叉树为空，不能遍历")
    }
  }

  //后序遍历查找
  def postOrderSearch(no: Int): TreeNode = {
    if (root != null) {
      root.postOrderSearch(no)
    } else {
      null
    }
  }

  //中序遍历查找
  def infixOrderSeacher(no: Int): TreeNode = {
    if (root != null) {
      return root.infixOrderSearch(no)
    } else {
      return null
    }
  }

  //前序查找
  def preOrderSearch(no: Int): TreeNode = {

    if (root != null) {
      return root.preOrderSearch(no)
    } else {
      //println("当前二叉树为空，不能查找")
      return null
    }
  }

  //删除节点
  def delNode(no: Int): Unit = {
    if (root != null) {
      //先处理一下root是不是要删除的
      if (root.no == no) {
        root = null
      } else {
        root.delNode(no)
      }
    }
  }
