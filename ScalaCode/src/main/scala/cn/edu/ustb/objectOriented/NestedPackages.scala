package cn {
  package edu {
    package ustb {
      //外层调用内层的属性和方法就必须要导包了

      import cn.edu.ustb.objectOriented.NestedPackages

      //在外层包中定义单例对象
      object Outer {
        var out: String = "out"

        def main(args: Array[String]): Unit = {
          NestedPackages.main(Array("0"))
        }
      }
      package objectOriented {
        /**
         * 演示Scala中和Java不同的包的嵌套
         */
        object NestedPackages {
          def main(args: Array[String]): Unit = {
            println("演示包的嵌套")
            //在内层包中定义单例对象
            println(Outer.out)
            Outer.out = "hello scala"
            println(Outer.out)
          }
        }
      }

    }

  }

}


