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

// 94aec9fbed989ece189a7e172c9cf41669050495152bc4c1dbf2a38d7fd85627
// 3196d7cdef3ec0f106d51354d82763dd47f8eb4b

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

function responseError(response, code, error) {
  response.writeHead(code);
  response.write(error.toString());
  response.end();
}

const server = http.createServer(async (request, response) => {
  console.info(request.url)
  if (!request.url.startsWith('/api/'))
  return responseError(response, 404, 'not an api request');
  if ((!request.headers['user-id']) && (!request.headers['user-password']))
    return responseError(response, 418, 'meow');

  let body = [];
  request
      .on('data',
          chunk => {
            body.push(chunk);
          })
      .on('end', () => {
        body = Buffer.concat(body).toString();
        validate(request.headers['user-id'], request.headers['user-password'])
            .then(x => console.info(x));
        // if (validate()) {}
        // resolve(request, body, response);
      });
});

server.listen({host: '0.0.0.0', port: 1004});