from flask import Flask, request, jsonify
import numpy as np
import pickle

app = Flask(__name__)

model = pickle.load(open('Model.pkl', 'rb'))

@app.route('/')
def home():
    return "HELLO WORLD"


@app.route('/predict', methods={'POST'})
def predict():
    preg = request.form.get('pregnancies')
    glu = request.form.get('glucose')
    bp = request.form.get('BP')
    skin = request.form.get('SkinThickness')
    insulin = request.form.get('Insulin')
    bmi = request.form.get('BMI')
    pedigree = float(request.form.get('pedigree'))
    age = request.form.get('age')

    userdata = (preg, glu, bp, skin, insulin, bmi, pedigree, age)
    data_array = np.asarray(userdata)
    reshape = data_array.reshape(1, - 1)
    prediction = model.predict(reshape)
    if prediction == 1:
        result = 'The Person is Diabetic :o'
    else:
        result = 'The Person is Non-diabetic ;)'
    return jsonify(str(result))


app.debug = 1
app.run()