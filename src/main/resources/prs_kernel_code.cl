__kernel void counting_multiple_4(__global char* _pok, __global long* fsize, __global int* _counter, __global char* _symbol)
{
  // na device_0 izvrsi promjenu countera i simbola
  // na device_1 ne promijeni counter ali promijeni simbol
  /*_counter[0]=5;
  _symbol[0]='g';*/
  int gid = get_global_id(0);
  if(gid==0){
    for(int i = 0; i < *fsize/2; i++)
    {
      for(int k=0; k<26; k++)
        if(_pok[i] == _symbol[k]) _counter[k]++;
    }
  }
  else if(gid==1){
    for(int i = *fsize/2; i < *fsize-1; i++)
    {
      for(int k=0; k<26; k++)
        if(_pok[i] == _symbol[k]) _counter[k+26]++;
    }
  }

}

__kernel void counting_single(__global char* _pok, __global long* fsize, __global int* _counter, __global char* _symbol)
{
  // na device_0 izvrsi promjenu countera i simbola
  // na device_1 ne promijeni counter ali promijeni simbol
  /*_counter[0]=5;
  _symbol[0]='g';*/
  int i;
  for(i = 0; i < *fsize-1; i++)
  {
    for(int k=0; k<26; k++)
      if(_pok[i] == _symbol[k]) _counter[k]++;
  }
}

__kernel void coding(__global char* _pok, __global long* fsize, __global int* _counter, __global char* _symbol, __global char* _pok_output, __global char* _code)
{
  int _cc = 0;
  for(int i = 0; i < *fsize-1; i++){
      for(int j = 0; j < 26; j++){
        if(_pok[i] == _symbol[j]){
          for(int s = 0; s < 10; s++){
            if(_code[j*s]=='_') break;
            _pok_output[_cc] = _code[j*s];
            _cc++;
          }
          break;
        }
      }
  }
}

__kernel void coding_multiple_4(__global char* _pok, __global long* fsize, __global int* _counter, __global char* _symbol, __global char* _pok_output_1, __global char* _code, __global char* _pok_output_2)
{
  int gid = get_global_id(0);

  int _cc1 = 0;
  int _cc2 = 0;
  int _cc3 = 0;
  int _cc4 = 0;


  if(gid==0){
    for(int i = 0; i < *fsize/2; i++){
        for(int j = 0; j < 26; j++){
          if(_pok[i] == _symbol[j]){
            for(int s = 0; s < 10; s++){
              if(_code[j*s]=='_') break;
              _pok_output_1[_cc1] = _code[j*s];
              _cc1++;
            }
            break;
          }
        }
    }
  }
  else if(gid==1){
    for(int i = *fsize/2; i < *fsize-1; i++){
        for(int j = 0; j < 26; j++){
          if(_pok[i] == _symbol[j]){
            for(int s = 0; s < 10; s++){
              if(_code[j*s]=='_') break;
              _pok_output_2[_cc2] = _code[j*s];
              _cc2++;
            }
            break;
          }
        }
    }
  }

}
