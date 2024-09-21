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

// e21fc56c1a272b630e0d1439079d0598cf8b8329

async function validate(user_id, password) {
  return (await database.query(
              `SELECT Password FROM users WHERE Name='${user_id}';`))
             .rows[0]
             .password === sha1(password);
}

const server = http.createServer(async (request, response) => {
  console.info(request.url)
  if (!request.url.startsWith('/api/'))
  return responseError(response, 404, 'not an api request');

  let body = [];
  request
      .on('data',
          chunk => {
            body.push(chunk);
          })
      .on('end', () => {
        body = Buffer.concat(body).toString();
        console.log(validate(
            request.headers['user-id'], request.headers['user-password']));
        // if (validate()) {}
        // resolve(request, body, response);
      });
});

server.listen({host: '0.0.0.0', port: 1004});