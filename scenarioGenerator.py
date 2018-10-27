from flask import Flask
from flask import request
from random import randint
import json


app = Flask(__name__)

@app.route('/generateScenario',methods=['POST'])
def generateScenario():
    req_data=request.get_json()
    generateParams(req_data)
    generateTables(req_data)
    '''
        App for hompage
    '''
    return 'HelloHack'

def generateTables(req_data):
    nombre="tables"+req_data['nombre']+".inc"
    n=req_data['n']
    limRatings=req_data['maxd']*2
    sec=[]
    sec.append('Parameters\n')
    sec.append('\tPuntaje(j) Puntaje del punto j\n \tCV(j) Vida promedio por ciudad\n')
    costoVida=req_data.get('costoVida')
    CV=[]
    if costoVida!=None:
        if costoVida=='Random':
            for i in range(0,n):
                CV.append(randint(1,100000))
        else:
            #Leer de un archivo el costo de vida por ciudad
            f = open(costoVida, "r")
            line=f.readline()
            i=0
            while line!=None and i<n:
                CV.append(line)
                line=f.readline()
                i+=1
            f.close()
    else:
        for i in range(0,n):
            CV.append(1)
    msg="\t/"
    for i in range (0,n):
        msg+='j'+str(i+1)+" "+str(CV[i])
        if i<n-1:
            msg+=", "
    msg+="/;\n"
    sec.append(msg)

    msg="\t"
    sec.append('table CT (j,k) Costos transporte\n')
    for j in range (0,n):
        msg+="\tj"+str(j+1)
    msg+="\n"
    costoTransporte=req_data.get('costoTransporte')
    CT=createMatrix(costoTransporte,n,n)
    for j in range(0,n):
        msg+="\tj"+str(j+1)
        for k in range (0,n):
            msg+="\t"+str(CT[j][k])
        msg+="\n"
    msg+=";\n"
    sec.append(msg)

    msg="\t"
    
    sec.append('table S (j,l) Puntaje de ciudad i por punto de interes\n')
    for l in range (0,limRatings):
        msg+="\tl"+str(l+1)
    msg+="\n"
    score=req_data.get('score')
    S=createMatrix(score,n,limRatings)

    for j in range(0,n):
        msg+="\tj"+str(j+1)
        for l in range (0,limRatings):
            msg+="\t"+str(S[j][l])
        msg+="\n"
    msg+=";\n"
    sec.append(msg)

    msg="\t"
    sec.append('table R (j,l) Cantidad de reviews de ciudad i por punto de interes\n')
    for l in range (0,limRatings):
        msg+="\tl"+str(l+1)
    msg+="\n"
    reviews=req_data.get('reviews')
    R=createMatrix(reviews,n,limRatings)
    for j in range(0,n):
        msg+="\tj"+str(j+1)
        for l in range (0,limRatings):
            msg+="\t"+str(R[j][l])
        msg+="\n"
    msg+=";\n"
    sec.append(msg)

    sec.append('LOOP((j),Puntaje(j)=sum(l,S(j,l)*(R(j,l))**0.5/5););')

    f=open(nombre,'w')
    f.writelines(sec)
    f.close()
    
def createMatrix(fileName,dim1,dim2):
    mat=[]
    if fileName!=None:
        if fileName=='Random':
            for j in range(0,dim1):
                mat.append([])
                for k in range (0,dim2):
                    mat[j].append(randint(1,100000))
        else:
            #Leer de un archivo el costo de vida por ciudad
            f = open(fileName, "r")
            line=f.readline()
            i=0
            while line!=None and i<dim1:
                data=line.split(' ')
                mat.append([])
                for x in data:
                    mat[i].append(data)
                i+=1
            f.close()            
    else:
        for j in range(0,dim1):
            mat.append([])
            for k in range (0,dim2):
                mat[j].append(1)
    return mat
        

def generateParams(req_data):
    nombre="params"+req_data['nombre']+".inc"
    inicio=req_data['inicio']
    maxd=req_data['maxd']
    mind=req_data['mind']
    p1=req_data['p1']
    p2=req_data['p2']
    d=req_data['d']-1
    n=req_data['n']

    sec=[]
    sec.append('Scalars\n')
    sec.append('\tinicio Ciudad de inicio /')
    sec.append(str(inicio))
    sec.append('/\n')
    sec.append('\tmaxd Max num dias /')
    sec.append(str(maxd))
    sec.append('/\n')
    sec.append('\tmind Min num dias /')
    sec.append(str(maxd))
    sec.append('/\n')
    sec.append('\tp1 Prioridad dias /')
    sec.append(str(p1))
    sec.append('/\n')
    sec.append('\tp2 Prioridad presupuesto /')
    sec.append(str(p2))
    sec.append('/\n;\n')
    sec.append('Scalar d Num dias; \n set i Dias /i0*i')
    sec.append(str(d))
    sec.append('/;\n')
    sec.append('d = card(i)\n')
    sec.append('Scalar n Num ciudades; \n set j Ciudades/j1*j')
    sec.append(str(n))
    sec.append('/;\n')
    sec.append('n = card(j)\n')
    sec.append('set l Num puntos de interes /l1*l')
    sec.append(str(2*maxd))
    sec.append('/;\n')
    sec.append('Alias (j,k);')

    f = open(nombre, "w")
    f.writelines(sec)
    f.close()

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')

