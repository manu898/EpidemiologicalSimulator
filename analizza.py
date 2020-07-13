
media_giorni = 0
media_tamponi = 0
media_casi_gov = 0
media_casi_sim = 0
media_risorse = 0
f = open("dati", 'w')
for n in range(1,10):
    fname = f"statistiche{n}.csv"
    tamponi = 0
    casi_totali_gov = 0
    casi_totali_sim = 0
    risorse = 0
    giorni = 0
    f.write(fname)
    f.write("\n")
    righe = []
    with open(fname) as c:
        righe = c.readlines()
    for i in range(1,len(righe)):
        dati = righe[i].split(', ')
        tamp = dati[-1].strip()
        tamponi += int(tamp)
    last = righe[-1].split(', ')
    casi_totali_gov = int(last[3]) + int(last[4]) + int(last[12]) + int(last[13])
    casi_totali_sim = int(last[6]) + int(last[7]) + int(last[12]) + int(last[13])
    risorse = int(last[1])
    giorni = int(last[0])
    media_giorni += giorni
    media_tamponi += tamponi
    media_casi_gov += casi_totali_gov
    media_casi_sim += casi_totali_sim
    media_risorse += risorse
    f.write(f"Giorni:  {giorni}\n")
    f.write(f"risorse rimaste: {risorse}\n")
    f.write(f"casi totali governo: {casi_totali_gov}\n")
    f.write(f"casi totali simulazione: {casi_totali_sim}\n")
    f.write(f"tamponi totali {tamponi}\n")
    f.write("------------------------------------------------\n\n")

media_giorni = media_giorni / 21.0
media_tamponi = media_tamponi / 21.0
media_casi_gov = media_casi_gov / 21.0
media_casi_sim = media_casi_sim / 21.0
media_risorse = media_risorse / 21.0
f.write("Dati complessivi\n")
f.write(f"Media durata: {media_giorni}\n")
f.write(f"Media risorse rimaste: {media_risorse}\n")
f.write(f"Media casi governo: {media_casi_gov}\n")
f.write(f"Media casi simulazione: {media_casi_sim}\n")
f.write(f"Media tamponi effettuati: {media_tamponi}\n")

f.close()
