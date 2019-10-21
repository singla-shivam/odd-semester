const Koa = require('koa')
const serve = require('koa-static')
const bodyParser = require('koa-body')
const Router = require('koa-router')
const {Student} = require('./database')

const app = new Koa();
const router = new Router()

router.post('/', (ctx) => {
    const body = ctx.request.body
    Student.create(body).then((s) => {
        console.log(s)
    }).catch(e => {
        console.error(e)
    }) 
    console.log(body)
    ctx.body = ctx.request.body
})

app.use(bodyParser({
    formidable: { uploadDir: './uploads' },
    multipart: true,
    urlencoded: true
}))

app.use(serve('./public'))

app.use(router.routes())

app.listen(3000)