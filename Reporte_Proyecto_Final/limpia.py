# -*- coding: utf-8 -*-
from nltk.corpus import stopwords           #para palabra irrelevantes

#carga la lista en ingles de las palabras irrelevantes
lis_palabras=stopwords.words('english')

irre=[]
for i in lis_palabras:
    irre.append(str(i))

#path=raw_input("dame direccion de archivo:")
path='base.txt'
twit=open(path,'r')
frases=twit.readlines()

l_frases=[]


def lista_str(lista):
    res=''
    for i in lista:
        res=res+' '+str(i)
    return res
    
for j in frases:
    var=j.split()
    #print var
    if var!=[]:
        l_frases.append(var)
        
#busca y quita las palabras irrelevantes en ingles
for k in l_frases:
    for l in k:
        for p in irre:
            if l==p:
                k.pop(k.index(p))

resultado=open('pregunta1_resultado.txt','w')
resultado=open('pregunta1_resultado.txt','a')
#imprime el texto limpio
for o in l_frases:
    resultado.write(lista_str(o))
    print o
    
resultado.close()

