const {Pool} = require('pg')
const http = require('http');
var crypto = require('crypto');

const database = new Pool({
  user: 'emptyy',
  password: 'ynes',
  host: '10.4.0.3',
  port: 5432,
  database: 'tth'
});


async function validate(user_id, password) {
  return (await database.query(
              `SELECT Password FROM users WHERE Name=${user_id};`))
             .rows[0]
             .password ===
      crypto.createHash('md5').update(password).digest('hex');
}

const server = http.createServer(async (request, response) => {
  console.log('REQ!')
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
        try {
          resolve(request, body, response);
        } catch (exception) {
          console.error(exception)
        }
      });
});

server.listen({host: '0.0.0.0', port: 1004});