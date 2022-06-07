# Week6_1
a) Для чего нужен Handler? Что с помощью него можно делать? Как он работает?

![image](https://user-images.githubusercontent.com/56700134/172434674-0fc3c93f-113b-4b96-96c7-4a84daa394b7.png)

Handler - это механизм, который позволяет работать с очередью сообщений. Он привязан к конкретному потоку и работает с его очередью.

Это позволяет реализовать отложенное по времени выполнение кода и самое главное выполнение кода в другом потоке.

Пример работы с Handler:
- создаем в основном потоке Handler

  ```kotlin
  handler = object : Handler(Looper.getMainLooper()){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                binding?.piTextView?.text = msg.obj.toString()
            }
        }
  ```

- в другом потоке обращаемся к Handler и с его помощью помещаем в очередь сообщение для него же самого

  ```kotlin
  runnable = object : Runnable{
              override fun run() {
                  while (running){
                      sleep(100)
                      val msg = Message.obtain()
                      msg.obj = piSpigot(counter)
                      handler?.sendMessage(msg)
                      counter ++
                 }
              }
          }
  Thread(runnable).start()
  ```

- система берет это сообщение, видит, что адресат – Handler, и  отправляет сообщение на обработку в Handler
- Handler, получив сообщение, обновит TextView
