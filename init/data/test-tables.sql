create extension hstore;

create table pgtest
(
    box         box                        not null,
    circle      circle                     not null,
    line        line                       not null,
    lseg        lseg                       not null,
    path        path                       not null,
    point       point                      not null,
    polygon     polygon                    not null,
    interval    interval                   not null,
    money       money                      not null,
    xml         xml                        not null,
    json        json                       not null,
    jsonb       jsonb                      not null,
    hstore      hstore                     not null,
    inet        inet                       not null,
    timestamp   timestamp                  not null,
    timestampz  timestamp with time zone   not null,
    time        time                       not null,
    timez       time with time zone        not null,
    date        date                       not null,
    uuid        uuid                       not null,
    boxes       box[]                      not null,
    circlees    circle[]                   not null,
    linees      line[]                     not null,
    lseges      lseg[]                     not null,
    pathes      path[]                     not null,
    pointes     point[]                    not null,
    polygones   polygon[]                  not null,
    intervales  interval[]                 not null,
    moneyes     money[]                    not null,
    xmles       xml[]                      not null,
    jsones      json[]                     not null,
    jsonbes     jsonb[]                    not null,
    hstores     hstore[]                   not null,
    inets       inet[]                     not null,
    timestamps  timestamp[]                not null,
    timestampzs timestamp with time zone[] not null,
    times       time[]                     not null,
    timezs      time with time zone[]      not null,
    dates       date[]                     not null,
    uuids       uuid[]                     not null
);

create table pgtestnull
(
    box         box,
    circle      circle,
    line        line,
    lseg        lseg,
    path        path,
    point       point,
    polygon     polygon,
    interval    interval,
    money       money,
    xml         xml,
    json        json,
    jsonb       jsonb,
    hstore      hstore,
    inet        inet,
    timestamp   timestamp,
    timestampz  timestamp with time zone,
    time        time,
    timez       time with time zone,
    date        date,
    uuid        uuid,
    boxes       box[],
    circlees    circle[],
    linees      line[],
    lseges      lseg[],
    pathes      path[],
    pointes     point[],
    polygones   polygon[],
    intervales  interval[],
    moneyes     money[],
    xmles       xml[],
    jsones      json[],
    jsonbes     jsonb[],
    hstores     hstore[],
    inets       inet[],
    timestamps  timestamp[],
    timestampzs timestamp with time zone[],
    times       time[],
    timezs      time with time zone[],
    dates       date[],
    uuids       uuid[]

);