from flask import Flask
import random

app = Flask(__name__)

@app.route('/random', methods=['GET'])
def get_random_number():
    rand_num = random.randint(1, 100)
    return str(rand_num)

if __name__ == '__main__':
    app.run(debug=True)