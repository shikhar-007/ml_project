from flask import Flask, request, render_template
import numpy as np
import pickle

app = Flask(__name__)

model = pickle.load(open('model.pkl', 'rb'))

@app.route('/')
def home():
    return render_template('index.html')


@app.route('/results', methods=['post'])
def results():
    preg = request.form.get('pregnancies')
    glu = request.form.get('glucose')
    bp = request.form.get('BP')
    skin = request.form.get('SkinThickness')
    insulin = request.form.get('Insulin')
    bmi = float(request.form.get('BMI'))
    pedigree = float(request.form.get('pedigree'))
    age = request.form.get('age')

    userdata = (preg, glu, bp, skin, insulin, bmi, pedigree, age)
    data_array = np.asarray(userdata)
    reshape = data_array.reshape(1,8)
    prediction = model.predict(reshape)
    if prediction[0] == 1:
        result = 'The Person is Diabetic!'
    else:
        result = 'The Person is Non-Diabetic ;)'
    return render_template('result.html', answer=result)


app.debug = 1
app.run(host='0.0.0.0',port=8080)