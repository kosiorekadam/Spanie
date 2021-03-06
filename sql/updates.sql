# nr pokoju, rodzaj pokoju, cena
# zalezy od sezonu
select nr_pokoju, p.rodzaj_pokoju, sp.cena_pokoju from SEZON s join SEZON_TYP_POKOJU sp on s.nr_sezonu = sp.nr_sezonu join POKOJE p on sp.rodzaj_pokoju = p.rodzaj_pokoju;


## przenieś pobyt do historii pobytów:
# dodaje wpis do historii pobytow
INSERT INTO HISTORIA_POBYTOW (nr_pobytu, nr_dokumentu_klienta, data_rozpoczecia, data_zakonczenia, nr_pokoju) select p.nr_pobytu, nr_dokumentu_klienta, data_rozpoczecia, data_zakonczenia, nr_pokoju  FROM POBYT p join POBYT_POKOJE pok on p.nr_pobytu = pok.nr_pobytu WHERE p.nr_pobytu = 10;
# usuwa wpis z pokoj
delete from POBYT_POKOJE where nr_pobytu = 10;
#usuwa wpis z uslug dodatkowych
delete from POBYT_USLUGA_DODATKOWA where nr_pobytu = 10;
# usuwa wpis z pobytow
delete from POBYT where nr_pobytu = 10;

# cena pobytu
# bo uslug dodatkowych moze byc duzo. a cena jest tylko jedna;
# a co jeśli usluga_dodatkowa null?
select sum(cena) + cena_pokoju from POBYT_USLUGA_DODATKOWA pob join POBYT_POKOJE poj on pob.nr_pobytu = poj.nr_pobytu where poj.nr_pobytu = 3;


# cena za pokoj * liczba dni w danym pobycie
select sum(cena_pokoju*(datediff(data_zakonczenia, data_rozpoczecia))) from POBYT pob right join POBYT_POKOJE pok on pob.nr_pobytu=pok.nr_pobytu where pob.nr_pobytu = 1;

# cena za usługi dodatkowe w danym pobycie
select sum(cena) from (select (cena*(datediff(data_zakonczenia, data_rozpoczecia))) as cena from POBYT_USLUGA_DODATKOWA where nr_pobytu = 2 group by nazwa_uslugi,data_zakonczenia,data_rozpoczecia,cena) A;
