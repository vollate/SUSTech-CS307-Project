# Project 2

>贾禹帆 栾钦策  

## Basic

### http/restful API

- use method `POST`
- the body format:

```json
{
    "type": $Op, // have 5 basic Op
    "content": [$SubOp, parameter1,parameter2, ...] 
    // parameter depend on SubOp
}
```

### content for each Op

#### UserOp

- [Login, UserName, Password]
- [CreateUser, UserName, Password, UserID, UserPhone]

#### PostOp

//TODO

#### SearchOp

//TODO

#### RelationOp

//TODO

#### ShowOp

//TODO

## Advanced

//TODO

### OpenGauss

Use docker to deploy. To prevent data loss, we use docker's volume to store data. Use `-v ${volume name}:/var/lib/opengauss` option when create container to store OpenGauss's data to a volume. In this way, the data in OpenGauss won't gone when the container is deleted. We could create another new container and mount previous volume to restore data.

### Anonymous reply

Add a boolean column for Reply and Secondary Reply in HDL to represent whether the reply is anonymous. When the client ask for the reply, check whether the reply is anonymous and decied send the author's information or not.

### HotTable

//TODO describe the hot table algorithm

### Http/RESTful server

Use Spring Boot's web framework. Core codes are below.

```java
@RestController
public class Handler {
    @Autowired
    private RequestSolver solver;

    @PostMapping(value = "/request")
    public RequestResponse query(@RequestBody ReqBody body) {
        System.out.println("type: " + body.type());
        System.out.println("content: " + body.content());
        try {
            RequestType requestType = RequestType.valueOf(body.type());
            ArrayList requestContent = body.content();
            String op = (String) requestContent.get(0);
            requestContent.remove(0);
            return new RequestResponse(switch (requestType) {
                case UserOp -> solver.solveUserOp(UserOpType.valueOf(op), requestContent);
                case PostOp -> solver.solvePostOp(PostOpType.valueOf(op), requestContent);
                case SearchOp -> solver.solveSearchOp(SearchOpType.valueOf(op), requestContent);
                case RelationOp -> solver.solveRelationOp(RelationOpType.valueOf(op), requestContent);
                case ShowOp -> solver.solveShowOp(ShowOpType.valueOf(op), requestContent);
                default -> new ArrayList();
            });
        } catch (Exception e) {
            return new RequestResponse();
        }
    }
}
```

### Database connection pools

Use Spring Boot's postgres JDBC, which contain connection pools by default

### Web GUI

Use Vue.js + BootStrap to write a responsive site, the project is in the folder `./the-end-bbs`. After built, copy the resources into Spring Boot project's "resource/static" folder

### Currency Server

