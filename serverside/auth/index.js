const {Pool} = require('pg')
const http = require('http');
const sha1 = require('sha1');

const database = new Pool({
  user: 'crona_adm',
  password: 'crona_adm_pass',
  host: '10.4.0.3',
  port: 5432,
  database: 'crona_adm'
});

async function validate(user_id, password) {
  console.info(await userExists(user_id))
  if (!(await userExists(user_id))) return false;
  console.info(sha1(password))
  return (await database.query(
              `SELECT password FROM users WHERE name='${user_id}';`))
             .rows[0]
             .password === sha1(password);
}

async function userExists(user_id) {
  return (await database.query(
              `SELECT password FROM users WHERE name='${user_id}';`))
             .rows.length !== 0;
}

async function register(user_id, password) {
  if (userExists(user_id)) return false;
  await database.query(`INSERT INTO users (name, password) VALUES ('${
      user_id}', '${password}');`);
  return true;
}

function responseCode(response, code, error) {
  response.writeHead(code);
  response.write(error.toString());
  response.end();
}

const server = http.createServer(async (request, response) => {
  console.info(request.url)
  if (!request.url.startsWith('/api/'))
  return responseCode(response, 404, 'not an api request');
  if ((!request.headers['user-id']) && (!request.headers['user-password']))
    return responseCode(response, 418, 'meow');
  if (request.url.startsWith('/api/reg')) {
    if (await userExists(request.headers['user-id']))
      return responseCode(response, 418, 'user exists');
    await database.query(`insert into users (name,password) values ('${
        request.headers['user-id']}','${request.headers['user-password']}}');`);
    return responseCode(response, 200, '')
  }
  if (request.url.startsWith('/api/auth')) {
    if (await validate(
            request.headers['user-id'], request.headers['user-password']))
      return responseCode(response, 200, '')
      return responseCode(response, 418, '')
  }
  let body = [];
  request
      .on('data',
          chunk => {
            body.push(chunk);
          })
      .on('end', () => {
        body = Buffer.concat(body).toString();
        validate(request.headers['user-id'], request.headers['user-password'])
            .then(async (ok) => {
              if (ok) {
                let rq = await fetch(
                    `http://localhost:5555${request.url}`,
                    {method: request.method, body: body})
                responseCode(response, rq.status, await rq.text())
              } else {
                responseCode(response, 418, 'wrong auth data')
              }
            });
      });
});

server.listen({host: '0.0.0.0', port: 1004});