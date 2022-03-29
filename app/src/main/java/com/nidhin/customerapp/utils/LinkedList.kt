package com.nidhin.customerapp.utils

object LinkedList {


    class Node(var data: Int?) {
        var next: Node? = null

        fun insert(data: Int): Node {
            var newNode = Node(data)
            newNode.next = this
            return newNode
        }

        fun print() {
            var node = this
            if (node != null) {
                System.out.println("Printing node data")
                System.out.println(node.data)
            }
            while (node.next != null) {
                node = node.next!!
                System.out.println(node.data)
            }
        }

        fun getSize(): Int {
            var node = this
            var len = 1
            while (node.next != null) {
                len++
                node = node.next!!
            }
            return len
        }
    }

    fun test() {

        var head = Node(1)
        head = head.insert(5)
        head = head.insert(6)
        head = head.insert(6)
        head = head.insert(1)
        head.print()


        var head2 = Node(3)
        head2 = head2.insert(1)
        head2.print()

        if (head.getSize() > head2.getSize()) {
            addPrecedingZeroes(head2, head)
        } else if (head2.getSize() > head.getSize()) {
            addPrecedingZeroes(head, head2)
        }
        head.print()
        head2.print()


    }

    fun addPrecedingZeroes(node1: Node, node2: Node) {
        var n1 = node1
        var n2 = node2
        while (n1.next != null && n2.next != null) {
            n1 = n1.next!!
            n2 = n2.next!!
        }
        while (n1.next == null && n2.next != null) {
            var node = Node(0)
            n1.next = node
            n1 = n1.next!!
            n2 = n2.next!!
        }
    }

    fun addNodes(node1: Node, node2: Node) {
        var n1 = node1
        var n2 = node2
        while (n1.next != null && n2.next != null) {
            n1 = n1.next!!
            n2 = n2.next!!
        }
        while (n1.next == null && n2.next != null) {
            var node = Node(0)
            n1.next = node
            n1 = n1.next!!
            n2 = n2.next!!
        }
    }

}