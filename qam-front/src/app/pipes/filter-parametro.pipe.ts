import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filterParametro'
})
export class FilterParametroPipe implements PipeTransform {

  transform(value: any, arg: any): unknown {

    if (arg === '' || arg.length < 3) return value;
    const resultPosts = [];
    for (const post of value) {
      if (post.title.toLowerCase().indexOf(arg.toLowerCase()) > -1) {
        resultPosts.push(post);
      };
    };
    return resultPosts;
  }


  
}
