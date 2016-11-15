# -*- coding: utf-8 -*-
from nltk.corpus import stopwords           #para palabra irrelevantes

#carga la lista en ingles de las palabras irrelevantes
lis_palabras=stopwords.words('english')

irre=[]
for i in lis_palabras:
    irre.append(str(i))

#path=raw_input("dame direccion de archivo:")
path='twitter.txt'
twit=open(path,'r')
frases=twit.readlines()

l_frases=[]

#quitamos todos links es decir los https
def lista_str(lista):
    res=''
    for i in lista:
        if not('http' in i):
            #print i
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

resultado=open('resultado.txt','w')
resultado=open('resultado.txt','a')
#imprime el texto limpio
for o in l_frases:
    resultado.write('\n'+(lista_str(o).lower())+'\n')
    #print o
    
resultado.close()

