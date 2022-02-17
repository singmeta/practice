const { MongoClient } = require("mongodb");

// mongodb 사용자 db와 collection uri 입니다 : 다음에 설정한 비밀번호를 넣으시면 됩니다
const uri =
  "mongodb+srv://singmeta:0000@cluster0.eqbzf.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

// client에 mongoclient 정의
const client = new MongoClient(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

// express 선언하고 port 넘버 할당

const express = require("express");
const app = express();
const port = 3005;

app.use("/static", express.static("phaser3"));

// monogo db write 라우트  - Neapolitan pizza , round를 몽고디비에 collection.insertOne을 통해서 입력시켜준다
app.get("/write", (req, res) => {
  res.send("<h1>mongodb write function</h1>");

  client.connect(async (err) => {
    const collection = client.db("singmeta").collection("singmeta");

    // perform actions on the collection object

    const doc = { name: "졸작데이터1", shape: "round" };
    const result = await collection.insertOne(doc);
    console.log(`A document was inserted with the _id: ${result.insertedId}`);

    client.close();
  });
});

//mongo db read 라우트 - junhyeong pizza를 collection에서 찾아서 cursor에 넣어주고 배열화 시켜서 console.log를 찍어준다
app.get("/read", (req, res) => {
  res.send("mongodb read function!");

  client.connect(async (err) => {
    const collection = client.db("singmeta").collection("singmeta");
    const cursor = collection.find({});
    const allValues = await cursor.toArray();

    await console.log(allValues);
    await console.log("this is test");

    await console.log("helloworld");
    // perform actions on the collection object

    await client.close();
    return await allValues;
  });
});

// 할당한 port 넘버로 서버를 작동시킨다.

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});
