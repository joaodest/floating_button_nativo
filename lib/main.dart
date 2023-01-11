import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: "Floating button",
      home: Home(),
      debugShowCheckedModeBanner: false,
    );
  }
}

class Home extends StatefulWidget {
  const Home({Key? key}) : super(key: key);

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final MethodChannel platform = MethodChannel("floating_button");

  @override
  void initState() {
    super.initState();
    platform.setMethodCallHandler((methodCall) async {
      if(methodCall.method == "touch"){
        setState(() {
          count += 1;
        });
      }
    });


  }

  int count = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Floating Button Demo"),
        centerTitle: true,
      ),
      body: Padding(
        padding: const EdgeInsets.all(8),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            Text(
              "$count",
              textAlign: TextAlign.center,
              style: const TextStyle(fontSize: 50),
            ),
            ElevatedButton(
                onPressed: (){
                  platform.invokeMethod('create');
                },
                child: const Text("Create")
            ),
            ElevatedButton(
                onPressed: (){
                  platform.invokeMethod('show');
                },
                child: const Text("Show")
            ),
            ElevatedButton(
                onPressed: (){
                  platform.invokeMethod('hide');
                },
                child: const Text("Hide")
            ),
            ElevatedButton(
                onPressed: (){
                  platform.invokeMethod("isShowing").then((isShowing){
                    print(isShowing);
                  });
                },
                child: const Text("Verify")
            ),
          ],
        ),
      ),
    );
  }
}
